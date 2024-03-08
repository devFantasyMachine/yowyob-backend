import type { NextApiRequest, NextApiResponse } from 'next'

type Data = {
	health: boolean;
}


export async function GET(req: NextApiRequest, res: NextApiResponse<Data>) {
	
	res.status(200).json({ health: true })
}

